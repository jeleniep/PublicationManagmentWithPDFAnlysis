FROM node:15
RUN mkdir -p /usr/src/app
WORKDIR /usr/src/app

COPY package*.json ./

COPY . /usr/src/app

RUN npm install -g typescript
RUN npm install -g nodemon
RUN npm install
RUN npm run build
RUN rm -rf node_modules types src && npm install --only=production

EXPOSE 4000
CMD [ "nodemon", "./dist/index.js" ]
