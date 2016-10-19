class LocalStorageCartService extends AbstractCartService implements ICartService {
    private CART_ITEMS_KEY = 'cartItems';
    private qService: ng.IQService;

    static $inject = ['localStorageService', '$q'];

    constructor(private localStorageService: ng.localStorage.ILocalStorageService,
                private $q: ng.IQService) {
        super();
        this.qService = $q;
    }

    add(product: IProduct): ng.IPromise<boolean> {
        var uuid: string = this.newUUID();
        var cartItem: ICartItem = {uuid: uuid, product : product};

        var cartItems: ICartItem[] = this.getCartItems();
        cartItems.push(cartItem);

        this.localStorageService.set(this.CART_ITEMS_KEY, cartItems)

        return this.truePromise();
    }

    remove(uuid: string): ng.IPromise<boolean> {
        var cartItems: ICartItem[] = this.getCartItems();
        cartItems = _.without(cartItems, _.findWhere(cartItems, {uuid: uuid}));

        this.localStorageService.set(this.CART_ITEMS_KEY, cartItems)

        return this.truePromise();
    }

    truePromise(): ng.IPromise<boolean>  {
        var deferred = this.qService.defer();
        deferred.resolve(true);
        return deferred.promise;
    }

    getAll(): ng.IPromise <ICartItem[]> {
        var deferred = this.qService.defer();
      deferred.resolve(this.getCartItems());
        return deferred.promise;
    }

    getCartItems(): ICartItem[] {
        return this.localStorageService.get(this.CART_ITEMS_KEY) || []
    }

    getCartId():string {
        return "localStorageCartService is not intend for production usage";
    }
}

eshop.service('localStorageCartService', LocalStorageCartService);
