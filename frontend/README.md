# Frontend - LATAM News Push

## Tech Stack

- React 18
- TypeScript
- Vite
- React Router
- React Query
- react-i18next

## Run

```bash
npm install
npm run dev
```

## Build

```bash
npm run build
```

## Notes

- 登录页目前为占位，不包含真实 Google OAuth。
- 默认读取 `VITE_API_BASE_URL`，未配置时回退到 `http://localhost:8080/api`。
- 已附带 `vercel.json`，后续只需替换后端域名并绑定环境变量即可部署。
