FROM node:12-alpine

WORKDIR /node-build

COPY package*.json ./

RUN npm install

COPY . .

RUN npm run build

# RUNTIME ENVIRONMENT
FROM node:12-alpine

RUN apk add --no-cache tzdata && \
    cp -fv /usr/share/zoneinfo/America/Sao_Paulo /etc/localtime && \
    echo "America/Sao_Paulo" > /etc/timezone && \
    apk del tzdata

WORKDIR /app

COPY --from=0 /node-build/package*.json ./

RUN npm install --production

COPY --from=0 /node-build/dist ./dist

ENV COMMIT=@@commit

CMD ["node", "dist/index.js"]
