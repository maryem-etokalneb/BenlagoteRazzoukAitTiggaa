# Avoir un environnement Node.js
FROM node:21-alpine AS build

#le dossier de travail (work directory is /app)
WORKDIR /app


COPY package*.json ./
RUN npm install

#Tout le code source est copié dans le conteneur(container)
COPY . .
RUN npm run build --prod

FROM nginx:alpine
COPY --from=build /app/dist/my-angular-app/browser /usr/share/nginx/html


EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]
