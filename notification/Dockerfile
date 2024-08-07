FROM node:20.10.0-alpine

WORKDIR /usr/src/app

# Install pnpm
RUN npm install -g pnpm

# Copy application dependency manifests and .env file to the container image.
COPY package*.json ./
COPY .env ./

# Install app dependencies using pnpm
RUN pnpm install

# Bundle app source
COPY . .

CMD ["pnpm","start"]