import dotenv from 'dotenv';
import path from 'path';
import fs from 'fs';

const appPath = path.join(__dirname, '..');
dotenv.config();

// Application setup
export const ENV = process.env.NODE_ENV || 'development';
export const APP_PORT = process.env.APP_PORT || 3000;

let appUrl: string;
let mongodbURI: string;
if (process.env.NODE_ENV === 'development') {
    mongodbURI = `mongodb://${process.env.DB_HOST}:${process.env.DB_PORT}/${process.env.DB_NAME}`;
    appUrl = `http://${process.env.DOMAIN}:${APP_PORT}`;
}
else {
    mongodbURI = `${process.env.DB_PREFIX}://${process.env.DB_USER}:${process.env.DB_PASS}@${process.env.DB_HOST}/${process.env.DB_NAME}${process.env.DB_QUERY}`;
    appUrl = `http://${process.env.DOMAIN}`;
}
export const APP_URL = appUrl;
export const MONGODB_URI = mongodbURI;


export const CONTROLLERS_PREFIX = 'v1';


export const {
    // App related
    APP_PATH,
    PUBLIC_VIDEOS_PATH,
    MASTER_KEY,
    // AWS related
    AWS_REGION,
    AWS_KEY_ID,
    AWS_SECRET_KEY,
    S3_BUCKET
} = process.env;
