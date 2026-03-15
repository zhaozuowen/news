import { Navigate, Route, Routes } from 'react-router-dom';
import { AppLayout } from './layouts/AppLayout';
import { LoginPage } from './pages/LoginPage';
import { SubscriptionsPage } from './pages/SubscriptionsPage';
import { NewsListPage } from './pages/NewsListPage';
import { NotificationHistoryPage } from './pages/NotificationHistoryPage';
import { SettingsPage } from './pages/SettingsPage';

export default function App() {
  return (
    <Routes>
      <Route element={<AppLayout />}>
        <Route path="/" element={<LoginPage />} />
        <Route path="/subscriptions" element={<SubscriptionsPage />} />
        <Route path="/news" element={<NewsListPage />} />
        <Route path="/history" element={<NotificationHistoryPage />} />
        <Route path="/settings" element={<SettingsPage />} />
      </Route>
      <Route path="*" element={<Navigate to="/" replace />} />
    </Routes>
  );
}
