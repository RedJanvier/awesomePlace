# BUILD FOR LOCAL DEVELOPMENT
FROM node:20.10.0-alpine AS development

WORKDIR /usr/src/app

# Install pnpm
RUN npm install -g pnpm

# Copy application dependency manifests and .env file to the container image.
COPY --chown=node:node package*.json ./
COPY --chown=node:node .env ./

# Install app dependencies using pnpm
RUN pnpm install

# Bundle app source
COPY --chown=node:node . .

# Use the node user
USER node

# BUILD FOR PRODUCTION
FROM node:20.10.0-alpine AS build

WORKDIR /usr/src/app

# Copy application dependency manifests and .env file to the container image.
COPY --chown=node:node package*.json ./
COPY --chown=node:node .env ./

# Install pnpm
RUN npm install -g pnpm

# In order to run `pnpm run build` we need access to the Nest CLI which is a dev dependency.
# In the previous development stage we ran `pnpm install` which installed all dependencies,
# so we can copy over the node_modules directory from the development image
COPY --chown=node:node --from=development /usr/src/app/node_modules ./node_modules

COPY --chown=node:node . .

# Run the build command which creates the production bundle
RUN pnpm run build

# Set NODE_ENV environment variable
ENV NODE_ENV production

# Running `pnpm install` removes the existing node_modules directory and passing in --only=production ensures that only the production dependencies are installed.
# This ensures that the node_modules directory is as optimized as possible
RUN pnpm install --only=production

USER node

