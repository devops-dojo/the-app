class RedisMircoserviceCartService extends AbstractCartService implements ICartService {

    private CART_ID = 'cartId';

    private rootScope: ng.IScope
    private qService: ng.IQService;
    private httpService: ng.IHttpService;

    static $inject = ['$rootScope', 'localStorageService', '$http', '$q', 'configuration'];

    constructor(private $rootScope: ng.IScope,
                private localStorageService: ng.localStorage.ILocalStorageService,
                private $http: ng.IHttpService,
                private $q: ng.IQService,
                private configuration:IConfiguration) {
        super();
        this.rootScope = $rootScope;
        this.httpService = $http;
        this.qService = $q;
    }

    add(product: IProduct): ng.IPromise<boolean> {
      var deferred = this.qService.defer();
      var uuid:string = this.newUUID();
      var cartItem:ICartItem = {uuid: uuid, product: product};

      if (!this.hasCardId()) {
        this.httpService.put(this.configuration.CART_SERVICE_PUT_URL, cartItem)
          .success((cartId:string) => {
            this.localStorageService.set(this.CART_ID, cartId.replace(/\"/g, ""));
            deferred.resolve(true);
          })
          .error((error) => {
            this.emitCartError(error);
            deferred.resolve(false);
          });
      } else {
        this.httpService.post(this.configuration.CART_SERVICE_POST_URL, cartItem, {params: {cartId: this.getCartId()}})
          .success((data) => deferred.resolve(true))
          .error((error) => {
            deferred.resolve(false);
          });
      }
      return deferred.promise;
    }

    remove(itemId: string): ng.IPromise<boolean> {
        var deferred = this.qService.defer();
        if(this.hasCardId()){
            this.httpService.delete(this.configuration.CART_SERVICE_DELETE_URL, {params : {cartId: this.getCartId(), itemId: itemId}})
                .success((data) => deferred.resolve(true))
                .error((error) => {
                  this.emitCartError(error);
                  deferred.resolve(false);
                });
        }
      return deferred.promise;
    }

    getAll(): ng.IPromise<ICartItem[]> {
        var deferred = this.qService.defer();

        if(this.hasCardId()){
          this.httpService.get(this.configuration.CART_SERVICE_GET_URL + this.getCartId())
            .success((data:ICart) => deferred.resolve(data.cartItems))
            .error((error:any) => {
              this.emitCartError(error);
              deferred.resolve(false);
            });
        }else{
            deferred.resolve([]);
        }

        return deferred.promise;
    }

    hasCardId(): boolean {
        var cardId: string = this.getCartId();
        return !this.isEmpty(cardId) && !this.isBlank(cardId);
    }

    getCartId(): string {
        return this.localStorageService.get(this.CART_ID);
    }

    emitCartError(error: any): void {
      this.rootScope.$emit(Eventnames.ADD_ALERT_MESSAGE, {type : "danger", message : "Cart can not be loaded, cart backend seems to be unreachable."});
    }
}

eshop.service('redisMircoserviceCartService', RedisMircoserviceCartService);
