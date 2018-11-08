//
// Copyright (c) Microsoft. All rights reserved.
// Licensed under the MIT license. See LICENSE.md file in the project root for full license information.
//
package com.microsoft.cognitiveservices.speech;


import com.microsoft.cognitiveservices.speech.util.Contracts;
import com.microsoft.cognitiveservices.speech.CancellationErrorCode;

/**
 * Contains detailed information about why a result was canceled.
 */
public class CancellationDetails {

    private CancellationReason reason;
    private String errorDetails;
    private CancellationErrorCode errorCode;
    private com.microsoft.cognitiveservices.speech.internal.CancellationDetails _cancellationImpl;

    /**
     * Creates an instance of CancellationDetails object for the canceled RecognitionResult.
     * @param result The result that was canceled.
     * @return The cancellation details object being created.
     */
    public static com.microsoft.cognitiveservices.speech.CancellationDetails fromResult(RecognitionResult result) {
        com.microsoft.cognitiveservices.speech.internal.CancellationDetails cancellation = com.microsoft.cognitiveservices.speech.internal.CancellationDetails.FromResult(result.getResultImpl());
        return new com.microsoft.cognitiveservices.speech.CancellationDetails(cancellation);
    }

    /*! \cond PROTECTED */

    protected CancellationDetails(com.microsoft.cognitiveservices.speech.internal.CancellationDetails cancellation) {
        Contracts.throwIfNull(cancellation, "cancellation");

        this._cancellationImpl = cancellation;
        this.reason = CancellationReason.values()[cancellation.getReason().swigValue() - 1]; // Native CancellationReason enum starts at 1!!
        this.errorCode = CancellationErrorCode.values()[cancellation.getErrorCode().swigValue()];
        this.errorDetails = cancellation.getErrorDetails();
    }

    /*! \endcond */

    /**
     * Explicitly frees any external resource attached to the object
     */
    public void close() {
        if (this._cancellationImpl != null) {
            this._cancellationImpl.delete();
        }
        this._cancellationImpl = null;
    }

    /**
     * The reason the recognition was canceled.
     * @return Specifies the reason canceled.
     */
    public CancellationReason getReason() {
        return this.reason;
    }

    /**
     * The error code in case of an unsuccessful recognition (when getReason() returns Error).
     * Added in version 1.1.0.
     * @return An error code that represents the error reason.
     */
    public CancellationErrorCode getErrorCode() {
        return this.errorCode;
    }

    /**
     * The error message in case of an unsuccessful recognition (when getReason() returns Error).
     * @return A String that represents the error details.
     */
    public String getErrorDetails() {
        return this.errorDetails;
    }

    /**
     * Returns a String that represents the cancellation details.
     * @return A String that represents the cancellation details.
     */
    @Override
    public String toString() {
        return "CancellationReason:" + this.reason +
                " ErrorCode: " + this.errorCode +
                " ErrorDetails:" + this.errorDetails;
    }
}
