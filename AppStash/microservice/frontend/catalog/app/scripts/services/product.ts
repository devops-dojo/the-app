interface IProductService {
    getProducts(): ng.IPromise <IProduct[]>
}

class ProductService implements IProductService {
    private httpService:ng.IHttpService;
    private qService:ng.IQService;
    private rootScope:ng.IScope

    static $inject = ['$http', '$q', '$rootScope', 'configuration'];

    constructor(private $http:ng.IHttpService,
                private $q:ng.IQService,
                private $rootScope:ng.IScope,
                private configuration:IConfiguration) {
        this.httpService = $http;
        this.qService = $q;
        this.rootScope = $rootScope;
        this.configuration = configuration;
    }

    getProducts():ng.IPromise <IProduct[]> {
        var deferred = this.qService.defer();
        this.httpService.get(this.configuration.PRODUCT_SERVICE_URL)
            .success((data) => deferred.resolve(data))
            .error((error:any) => {
                this.rootScope.$emit(Eventnames.ADD_ALERT_MESSAGE, {type: "danger", message: "Products can not be loaded, product backend seems to be unreachable."});
            });
        return deferred.promise;
    }
}

eshop.service('productService', ProductService);