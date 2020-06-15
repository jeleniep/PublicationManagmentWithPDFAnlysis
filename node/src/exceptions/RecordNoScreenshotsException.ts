import HttpException from "./HttpException";

class RecordNoScreenshotsException extends HttpException {
    constructor(token: string) {
        super(
            400,
            'no-screenshots',
            `This recording ${token} does not have any screenshots associated with it`
        );
    }
}

export default RecordNoScreenshotsException;
