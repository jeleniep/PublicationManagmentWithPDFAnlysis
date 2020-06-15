import HttpException from "./HttpException";

class RecordFilenameEmptyException extends HttpException {
    constructor() {
        super(
            400,
            'filename-empty-name',
            'Filename contains just the .mp4 extension'
        );
    }
}

export default RecordFilenameEmptyException;
