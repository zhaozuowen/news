import axios from 'axios';
import type { CreateSubscriptionPayload, NewsArticle, NotificationHistoryItem, NotificationSettings, Subscription } from '../types';

const api = axios.create({
  baseURL: import.meta.env.VITE_API_BASE_URL ?? 'http://localhost:8080/api'
});

export const fetchSubscriptions = async (): Promise<Subscription[]> => {
  const { data } = await api.get('/subscriptions');
  return data;
};

export const createSubscription = async (payload: CreateSubscriptionPayload): Promise<Subscription> => {
  const { data } = await api.post('/subscriptions', payload);
  return data;
};

export const fetchNews = async (): Promise<NewsArticle[]> => {
  const { data } = await api.get('/news/feed');
  return data;
};

export const fetchNotificationHistory = async (): Promise<NotificationHistoryItem[]> => {
  const { data } = await api.get('/notifications/history');
  return data;
};

export const fetchSettings = async (): Promise<NotificationSettings> => {
  const { data } = await api.get('/preferences/notifications');
  return data;
};

export const updateSettings = async (payload: NotificationSettings): Promise<NotificationSettings> => {
  const { data } = await api.put('/preferences/notifications', payload);
  return data;
};
