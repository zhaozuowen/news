import { NavLink } from 'react-router-dom';
import { useTranslation } from 'react-i18next';

export function NavBar() {
  const { t } = useTranslation();

  return (
    <nav className="sidebar">
      <h1>{t('appName')}</h1>
      <NavLink to="/">{t('login')}</NavLink>
      <NavLink to="/subscriptions">{t('subscriptions')}</NavLink>
      <NavLink to="/news">{t('news')}</NavLink>
      <NavLink to="/history">{t('history')}</NavLink>
      <NavLink to="/settings">{t('settings')}</NavLink>
    </nav>
  );
}
