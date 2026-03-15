import { useTranslation } from 'react-i18next';

export function LoginPage() {
  const { t } = useTranslation();

  return (
    <section className="card hero">
      <h2>{t('login')}</h2>
      <p>{t('welcome')}</p>
      <button className="primary-button">Google OAuth (placeholder)</button>
      <p className="muted">{t('loginPlaceholder')}</p>
    </section>
  );
}
