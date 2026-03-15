import i18n from 'i18next';
import { initReactI18next } from 'react-i18next';
import esMX from './locales/es-MX.json';
import ptBR from './locales/pt-BR.json';

const resources = {
  'es-MX': { translation: esMX },
  'pt-BR': { translation: ptBR }
};

i18n.use(initReactI18next).init({
  resources,
  lng: 'es-MX',
  fallbackLng: 'es-MX',
  interpolation: { escapeValue: false }
});

export default i18n;
