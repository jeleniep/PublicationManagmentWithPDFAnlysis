import HttpException from "./HttpException";

class RecordStillRecordingException extends HttpException {
    constructor(token: string) {
        super(
            500,
            'record-still-recording',
            `This recording is still being recorded: ${token}`
        );
    }
}

export default RecordStillRecordingException;
