import { useQuery } from '@tanstack/react-query';
import { useTranslation } from 'react-i18next';
import { fetchNotificationHistory } from '../services/api';

export function NotificationHistoryPage() {
  const { t } = useTranslation();
  const { data = [], isLoading } = useQuery({ queryKey: ['history'], queryFn: fetchNotificationHistory });

  return (
    <section className="card">
      <h2>{t('history')}</h2>
      {isLoading ? <p>Loading...</p> : (
        <ul className="timeline">
          {data.map((item) => (
            <li key={item.id}>
              <strong>{item.title}</strong>
              <p>{item.body}</p>
              <p className="muted">{item.topic} · {item.status} · {new Date(item.sentAt).toLocaleString()}</p>
            </li>
          ))}
        </ul>
      )}
    </section>
  );
}
