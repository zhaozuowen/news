export type Subscription = {
  id: number;
  topic: string;
  locale: string;
  countryCode: string;
  notificationsEnabled: boolean;
  heatLevel: string;
};

export type CreateSubscriptionPayload = {
  topic: string;
  locale: string;
  countryCode: string;
};

export type NewsArticle = {
  id: number;
  title: string;
  summary: string;
  sourceName: string;
  countryCode: string;
  language: string;
  publishedAt: string;
  url: string;
};

export type NotificationHistoryItem = {
  id: number;
  title: string;
  body: string;
  status: string;
  sentAt: string;
  topic: string;
};

export type NotificationSettings = {
  locale: string;
  timezone: string;
  quietHoursStart: string;
  quietHoursEnd: string;
  maxPushPerDay: number;
  digestMode: string;
};
