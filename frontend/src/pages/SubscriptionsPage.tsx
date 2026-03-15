import { useMemo, useState } from 'react';
import { useMutation, useQuery, useQueryClient } from '@tanstack/react-query';
import { useTranslation } from 'react-i18next';
import { createSubscription, fetchSubscriptions } from '../services/api';

export function SubscriptionsPage() {
  const { t, i18n } = useTranslation();
  const queryClient = useQueryClient();
  const { data = [], isLoading } = useQuery({ queryKey: ['subscriptions'], queryFn: fetchSubscriptions });
  const [isFormOpen, setIsFormOpen] = useState(false);
  const [topic, setTopic] = useState('');
  const [countryCode, setCountryCode] = useState('MX');

  const locale = useMemo(() => (i18n.language === 'pt-BR' ? 'pt-BR' : 'es-MX'), [i18n.language]);

  const mutation = useMutation({
    mutationFn: createSubscription,
    onSuccess: async () => {
      setTopic('');
      setCountryCode('MX');
      setIsFormOpen(false);
      await queryClient.invalidateQueries({ queryKey: ['subscriptions'] });
    }
  });

  const handleSubmit = async (event: React.FormEvent<HTMLFormElement>) => {
    event.preventDefault();
    if (!topic.trim()) return;

    await mutation.mutateAsync({
      topic: topic.trim(),
      locale,
      countryCode
    });
  };

  return (
    <section className="card">
      <div className="section-header">
        <h2>{t('subscriptions')}</h2>
        <button className="primary-button" type="button" onClick={() => setIsFormOpen((prev) => !prev)}>
          {isFormOpen ? 'Cancel' : t('addSubscription')}
        </button>
      </div>

      {isFormOpen && (
        <form onSubmit={handleSubmit} style={{ display: 'grid', gap: 12, marginBottom: 20 }}>
          <input
            value={topic}
            onChange={(event) => setTopic(event.target.value)}
            placeholder="inflación en México"
          />
          <select value={countryCode} onChange={(event) => setCountryCode(event.target.value)}>
            <option value="MX">Mexico</option>
            <option value="BR">Brazil</option>
            <option value="AR">Argentina</option>
            <option value="CO">Colombia</option>
          </select>
          <button className="primary-button" type="submit" disabled={mutation.isPending}>
            {mutation.isPending ? 'Saving...' : t('addSubscription')}
          </button>
          {mutation.isError && <p style={{ color: '#dc2626', margin: 0 }}>Failed to create subscription.</p>}
        </form>
      )}

      {isLoading ? <p>Loading...</p> : (
        <table>
          <thead>
            <tr>
              <th>{t('topic')}</th>
              <th>{t('country')}</th>
              <th>Locale</th>
              <th>{t('status')}</th>
            </tr>
          </thead>
          <tbody>
            {data.map((item) => (
              <tr key={item.id}>
                <td>{item.topic}</td>
                <td>{item.countryCode}</td>
                <td>{item.locale}</td>
                <td>{item.notificationsEnabled ? 'Enabled' : 'Paused'} / {item.heatLevel}</td>
              </tr>
            ))}
          </tbody>
        </table>
      )}
    </section>
  );
}
