FROM --platform=linux/amd64 node:16.17.0-bullseye-slim

WORKDIR /usr/src/app

COPY ./package.json ./yarn.lock ./

RUN yarn install

COPY . .

# THIS IS TO ENABLE PRISMA DO DETECT REQUIRED FILES
RUN apt-get update && apt-get install -y openssl libssl-dev

RUN yarn prisma:generate && yarn build

CMD ["yarn","start"]