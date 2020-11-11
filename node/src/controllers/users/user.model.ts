import mongoose, { Schema, Document } from 'mongoose';
import { USER, ADMIN } from '../../constants';



export interface IUser {
    email: string,
    hash: string,
    username: string,
    profile?: string,
    // token?: string,
}

export const UserSchema: mongoose.Schema<any> = new Schema({
    email: { type: String, required: true, unique: true, index: true },
    hash: { type: String, required: true },
    username: { type: String, required: true, unique: true, index: true },
    profile: { type: String, enum: [ADMIN, USER], required: false },
    // token: { type: String, required: false, index: { unique: true, sparse: true } }
}, {
    timestamps: true
});

export type UserType = IUser & Document

const User: mongoose.Model<UserType> = mongoose.model<UserType>('User', UserSchema);
export default User;
