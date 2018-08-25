package com.study.baselibrary.network;

/*响应对象的封装*/
public class NetResponse
{
    private boolean hasError;
    private int errorType;    //1为Cookie失效
    private String errorMessage;
    //正确的结果
    private String result;

    public boolean isHasError()
    {
        return hasError;
    }

    public void setHasError(boolean hasError)
    {
        this.hasError = hasError;
    }

    public String getErrorMessage()
    {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage)
    {
        this.errorMessage = errorMessage;
    }

    public String getResult()
    {
        return result;
    }

    public void setResult(String result)
    {
        this.result = result;
    }

    public int getErrorType()
    {
        return errorType;
    }

    public void setErrorType(int errorType)
    {
        this.errorType = errorType;
    }
}
