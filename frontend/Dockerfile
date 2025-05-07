# Usar a imagem oficial do Node.js
FROM node:latest

# Definir diretório de trabalho dentro do contêiner
WORKDIR /app

# Copiar arquivos do projeto para o contêiner
COPY package.json package-lock.json ./
RUN npm install

# Copiar o restante do código para o contêiner
COPY . .

# Construir o projeto Angular
RUN npm run build --prod

# Usar o servidor Nginx para servir o frontend
FROM nginx:alpine
COPY --from=0 /app/dist/frontend /usr/share/nginx/html

# Expor a porta 80
EXPOSE 80

# Comando para iniciar o Nginx
CMD ["nginx", "-g", "daemon off;"]
