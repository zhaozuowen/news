import { useQuery } from '@tanstack/react-query';
import { useTranslation } from 'react-i18next';
import { fetchSubscriptions } from '../services/api';

export function SubscriptionsPage() {
  const { t } = useTranslation();
  const { data = [], isLoading } = useQuery({ queryKey: ['subscriptions'], queryFn: fetchSubscriptions });

  return (
    <section className="card">
      <div className="section-header">
        <h2>{t('subscriptions')}</h2>
        <button className="primary-button">{t('addSubscription')}</button>
      </div>
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
