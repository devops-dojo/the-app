class Eventnames{
    public static ADD_TO_CART: string = "ADD_TO_CART";
    public static REMOVE_FROM_CARD: string = "REMOVE_FROM_CARD";
    public static ADD_ALERT_MESSAGE: string = "ADD_ALERT_MESSAGE";
}

interface IAlertItem {
    type: string;
    message: string;
}

interface INavigationItem{
    'sum': number;
    'name': string;
    'urlname': string;
    '_id': string;
}

interface IProduct {
    'id' : string;
    'articleId' : string;
    'name' : string;
    'urlname' : string;
    'description': string;
    'productType': string;
    'price' : number;
}

interface ICartItem {
    uuid: string;
    product: IProduct;
}

interface ICart {
    uuid: string;
    cartItems: ICartItem[];
}


interface ICartService {
    add(product: IProduct): ng.IPromise<boolean>;
    remove(uuid: string): ng.IPromise<boolean>;
    getAll(): ng.IPromise <ICartItem[]>;
    getCartId(): string;
}

interface IConfiguration {
    "NAVIGATION_SERVICE_URL": string;
    "PRODUCT_SERVICE_URL": string;
    "CART_SERVICE_IMPL": string;
    "CART_SERVICE_GET_URL":  string;
    "CART_SERVICE_PUT_URL":  string;
    "CART_SERVICE_POST_URL":  string;
    "CART_SERVICE_DELETE_URL":  string;
    "CHECKOUT_BASE_URL":  string;
    "GLOBAL_DISCOUNT" : number;
}

class AbstractCartService {

    newUUID(): string {
        return 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g, (c) => {
            var r = Math.random()*16|0, v = c == 'x' ? r : (r&0x3|0x8);
            return v.toString(16);
        });
    }

    isEmpty(str: string): boolean {
        return (!str || 0 === str.length);
    }

    isBlank(str: string): boolean {
        return (!str || /^\s*$/.test(str));
    }
}
