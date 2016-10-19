/// <reference path="../services/localstorage-cart.ts"/>
/// <reference path="../services/redis-microservice-cart.ts"/>

interface ICartServiceResolver extends ICartService {
    resolve(): ICartService;
}

class CartServiceResolver implements ICartServiceResolver {

    static $inject = ['localStorageCartService', 'redisMircoserviceCartService', 'configuration'];

    constructor(private localStorageCartService:ICartService,
                private redisMircoserviceCartService:ICartService,
                private configuration:IConfiguration) {
    }

    add(product:IProduct) {
        return this.resolve().add(product);
    }

    remove(uuid:string) {
        return this.resolve().remove(uuid);
    }

    getAll():ng.IPromise<ICartItem[]> {
        return this.resolve().getAll();
    }

    getCartId():string {
        return this.resolve().getCartId();
    }

    resolve():ICartService {
        switch (this.configuration.CART_SERVICE_IMPL) {
            case "redis-microservice" :
                return this.redisMircoserviceCartService;
            default:
                return this.localStorageCartService;
        }
    }
}

eshop.service('cartServiceResolver', CartServiceResolver);
