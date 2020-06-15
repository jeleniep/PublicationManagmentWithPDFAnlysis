import HttpException from "./HttpException";

class RecordVideoAlreadyUploadedException extends HttpException {
    constructor(videoUri: string) {
        super(
            422,
            'video-already-uploaded',
            `This video was already uploaded to ${videoUri}`
        );
    }
}

export default RecordVideoAlreadyUploadedException;
