export interface RESTRequest {
    resource: string,
    method: string,
    body: string | null,
    parameters: { [name: string]: string } | null,
}

export interface RESTResponse {
    statusCode: number,
    headers?: { [key: string]: string },
    body: string
}

export interface ErrorResponse {
    error: string;
}
