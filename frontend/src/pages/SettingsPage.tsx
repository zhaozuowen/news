import { FormEvent, useEffect, useState } from 'react';
import { useMutation, useQuery } from '@tanstack/react-query';
import { useTranslation } from 'react-i18next';
import { fetchSettings, updateSettings } from '../services/api';
import type { NotificationSettings } from '../types';

const defaultSettings: NotificationSettings = {
  locale: 'es-MX',
  timezone: 'America/Mexico_City',
  quietHoursStart: '22:00',
  quietHoursEnd: '07:00',
  maxPushPerDay: 8,
  digestMode: 'REALTIME'
};

export function SettingsPage() {
  const { t, i18n } = useTranslation();
  const { data } = useQuery({ queryKey: ['settings'], queryFn: fetchSettings });
  const [form, setForm] = useState<NotificationSettings>(defaultSettings);

  useEffect(() => {
    if (data) {
      setForm(data);
      i18n.changeLanguage(data.locale);
    }
  }, [data, i18n]);

  const mutation = useMutation({
    mutationFn: updateSettings,
    onSuccess: (result) => {
      setForm(result);
      i18n.changeLanguage(result.locale);
    }
  });

  const onSubmit = (event: FormEvent) => {
    event.preventDefault();
    mutation.mutate(form);
  };

  return (
    <section className="card">
      <h2>{t('settings')}</h2>
      <form className="form-grid" onSubmit={onSubmit}>
        <label>
          {t('locale')}
          <select value={form.locale} onChange={(e) => setForm({ ...form, locale: e.target.value })}>
            <option value="es-MX">Español (México)</option>
            <option value="pt-BR">Português (Brasil)</option>
          </select>
        </label>
        <label>
          Timezone
          <input value={form.timezone} onChange={(e) => setForm({ ...form, timezone: e.target.value })} />
        </label>
        <label>
          {t('quietHours')}
          <input value={form.quietHoursStart} onChange={(e) => setForm({ ...form, quietHoursStart: e.target.value })} />
        </label>
        <label>
          Quiet end
          <input value={form.quietHoursEnd} onChange={(e) => setForm({ ...form, quietHoursEnd: e.target.value })} />
        </label>
        <label>
          {t('frequency')}
          <input type="number" value={form.maxPushPerDay} onChange={(e) => setForm({ ...form, maxPushPerDay: Number(e.target.value) })} />
        </label>
        <label>
          Digest mode
          <select value={form.digestMode} onChange={(e) => setForm({ ...form, digestMode: e.target.value })}>
            <option value="REALTIME">Realtime</option>
            <option value="DIGEST">Digest</option>
          </select>
        </label>
        <button className="primary-button" type="submit">{mutation.isPending ? 'Saving...' : t('save')}</button>
      </form>
    </section>
  );
}
