import { useQuery } from '@tanstack/react-query';
import { useTranslation } from 'react-i18next';
import { fetchNews } from '../services/api';

export function NewsListPage() {
  const { t } = useTranslation();
  const { data = [], isLoading } = useQuery({ queryKey: ['news'], queryFn: fetchNews });

  return (
    <section className="card">
      <h2>{t('news')}</h2>
      {isLoading ? <p>Loading...</p> : (
        <div className="news-grid">
          {data.map((article) => (
            <article key={article.id} className="news-card">
              <span className="badge">{article.countryCode}</span>
              <h3>{article.title}</h3>
              <p>{article.summary}</p>
              <p className="muted">{t('source')}: {article.sourceName}</p>
              <p className="muted">{t('publishedAt')}: {new Date(article.publishedAt).toLocaleString()}</p>
              <a href={article.url} target="_blank" rel="noreferrer">Open source</a>
            </article>
          ))}
        </div>
      )}
    </section>
  );
}
