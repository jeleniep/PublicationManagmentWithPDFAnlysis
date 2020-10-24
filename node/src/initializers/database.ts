import mongoose from 'mongoose';
import { MONGODB_URI } from '../config';

export const initializeDb = () => {
    mongoose.connect(
        MONGODB_URI,
        {
            useNewUrlParser: true,
            useCreateIndex: true,
            bufferMaxEntries: 0, 
            reconnectTries: 5000, 
            poolSize: 10
        }
    );
    mongoose.Promise = global.Promise;
    mongoose.set('useFindAndModify', false);
    const db = mongoose.connection;
    db.on('error', console.error.bind(console, 'MongoDB connection error:'));
};
