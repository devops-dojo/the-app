interface INavigationService{
    getNavigation(): ng.IPromise <INavigationItem[]>
}

class NavigationService implements INavigationService{

    private rootScope: ng.IScope
    private httpService: ng.IHttpService;
    private qService: ng.IQService;

    static $inject = ['$rootScope', '$http', '$q', 'configuration'];

    constructor(private $rootScope: ng.IScope,
                private $http: ng.IHttpService,
                private $q: ng.IQService,
                private configuration:IConfiguration) {
        this.rootScope = $rootScope;
        this.httpService = $http;
        this.qService = $q;
    }

    getNavigation() : ng.IPromise <INavigationItem[]> {
        var deferred = this.qService.defer();
        this.httpService.get(this.configuration.NAVIGATION_SERVICE_URL)
          .success((data :INavigationItem[]) => deferred.resolve(data))
          .error((error:any) => {
                this.rootScope.$emit(Eventnames.ADD_ALERT_MESSAGE, {type : "danger", message : "Navigation can not be loaded, navigation backend seems to be unreachable."});
            });
        return deferred.promise;
    }
}

eshop.service('navigationService', NavigationService);
