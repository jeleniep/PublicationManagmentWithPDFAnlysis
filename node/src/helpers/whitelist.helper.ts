import normalizeUrl from 'normalize-url';

export const parseUrl = (url: string, stripProtocol: boolean): string => (
    normalizeUrl(
        url.trim(),
        {
            normalizeProtocol: true,
            stripHash: true,
            removeTrailingSlash: true,
            stripProtocol
        }
    )
);
