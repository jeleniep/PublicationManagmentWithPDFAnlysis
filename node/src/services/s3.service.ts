import aws from 'aws-sdk';
import fs from 'fs';
import mimeTypes from 'mime-types';
import cryptoRandomString from 'crypto-random-string';
import { S3_BUCKET } from '../config';

class S3Service {
    private s3: any;

    constructor() {
        this.s3 = new aws.S3();
    }

    private uploadPromise(params: any): Promise<any> {
        return new Promise((resolve, reject) => {
            this.s3.upload(params, (err: any, url: string) => {
                err ? reject(err) : resolve(url);
            });
        })
    }

    public static async getSignedUrl(operation: string, key: string, params: any): Promise<any> {
        const instance: S3Service = new this();
        const s3Params: any = Object.assign(
            {
                Bucket: S3_BUCKET,
                Key: key,
                Expires: 3600
            },
            params
        );
        const data: any = await instance.s3.getSignedUrlPromise(operation, s3Params);
        return data;
    }

    public static async getSignedViewUrl(key: string): Promise<any> {
        return await this.getSignedUrl(
            'getObject',
            key,
            {}
        );
    }

    public static async getSignedDownloadUrl(key: string, filename: string): Promise<any> {
        const contentDispositionHeader = filename
            ? `attachment; filename="${filename}"`
            : 'attachment';

        return this.getSignedUrl(
            'getObject',
            key,
            {
                ResponseContentDisposition: contentDispositionHeader,
                ResponseContentType: 'video/mp4',
            }
        );
    }

    public static async getSignedUploadUrl(key: string, filetypes: string | Array<string>): Promise<string | Array<string>> {
        const signedUrls = [];
        filetypes = (typeof filetypes === 'string' ? [filetypes] : filetypes);

        for (let i = 0; i < filetypes.length; ++i) {
            const extension = mimeTypes.extension(filetypes[i]);
            signedUrls.push(
                await this.getSignedUrl(
                    'putObject',
                    `${key}/images/${cryptoRandomString({ length: 16, type: 'url-safe' })}${i}${extension ? `.${extension}` : ''}`,
                    {
                        ACL: 'public-read'
                        // ContentEncoding: 'base64'
                    }
                )
            );
        }
        return typeof filetypes === 'string' ? signedUrls[0] : signedUrls;
    }

    public static async upload(filepath: string, key: string, filetype: string): Promise<any> {
        const fileContent = fs.readFileSync(filepath);
        const instance: S3Service = new this();
        const s3Params: any = {
            Bucket: S3_BUCKET,
            Key: key,
            Expires: 3600,
            ContentType: filetype,
            ACL: 'public-read',
            Body: fileContent

        };
        const data: any = await instance.uploadPromise(s3Params);

        return data;
    }
}
export default S3Service;
