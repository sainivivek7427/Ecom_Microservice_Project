package com.micro.auth.model;

public class MergeGuestToUserCartResponse {

    private boolean isExistUserCart;
    private boolean isExistGuestCart;
    private boolean success;

    public MergeGuestToUserCartResponse(boolean isExistUserCart, boolean isExistGuestCart, boolean success) {
        this.isExistUserCart = isExistUserCart;
        this.isExistGuestCart = isExistGuestCart;
        this.success = success;
    }

    public MergeGuestToUserCartResponse() {
    }

    public boolean isExistUserCart() {
        return isExistUserCart;
    }

    public void setExistUserCart(boolean existUserCart) {
        isExistUserCart = existUserCart;
    }

    public boolean isExistGuestCart() {
        return isExistGuestCart;
    }

    public void setExistGuestCart(boolean existGuestCart) {
        isExistGuestCart = existGuestCart;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
