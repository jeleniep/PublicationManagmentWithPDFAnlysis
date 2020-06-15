import HttpException from "./HttpException";

class RecordEmptyMetadataException extends HttpException {
    constructor(token: string) {
        super(
            400,
            'record-empty-metadata',
            `This recording has no metadata: ${token}`
        );
    }
}

export default RecordEmptyMetadataException;
