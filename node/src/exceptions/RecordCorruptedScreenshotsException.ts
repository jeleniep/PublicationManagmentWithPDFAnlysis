import HttpException from "./HttpException";

class RecordCorruptedScreenshotsException extends HttpException {
    constructor() {
        super(
            422,
            'corrupted-screenshots',
            `Screenshots got corrupted`
        );
    }
}

export default RecordCorruptedScreenshotsException;
