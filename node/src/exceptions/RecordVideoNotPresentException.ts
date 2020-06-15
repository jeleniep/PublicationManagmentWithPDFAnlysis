import HttpException from "./HttpException";

class RecordVideoNotPresentException extends HttpException {
    constructor() {
        super(
            422,
            'video-not-present',
            `Couldn't find url for this recording`
        );
    }
}

export default RecordVideoNotPresentException;
