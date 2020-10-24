import dotenv from 'dotenv';
import path from 'path';
import fs from 'fs';

const appPath = path.join(__dirname, '..');
let secretsFile = fs.readFileSync('/run/secrets/secrets');
let secretsJson = JSON.parse(secretsFile.toString());

// Application setup
export const ENV = secretsJson.NODE_ENV || 'development';
export const APP_PORT = secretsJson.APP_PORT || 3000;

let mongodbURI: string;
mongodbURI = `${secretsJson.DB_PREFIX}://${secretsJson.DB_USER}:${secretsJson.DB_PASS}@${secretsJson.DB_HOST}`;

export const MONGODB_URI = mongodbURI;


export const CONTROLLERS_PREFIX = 'v1';


export const {

} = secretsJson;
