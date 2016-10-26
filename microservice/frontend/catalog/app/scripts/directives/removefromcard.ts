interface IShopRemoveFromCartScope extends ng.IScope {
    uuid: string
    remove(uuid: string);
}


eshop.directive("shopRemoveFromCart",['$rootScope',  ($rootScope): ng.IDirective => {
    var directive: ng.IDirective = {};

    directive.restrict = "AE";
    directive.templateUrl = "/partials/removefromcart.html";
    directive.replace = true;

    directive.scope = {
        uuid: "=uuid"
    };

    directive.link = (scope: IShopRemoveFromCartScope) => {
        scope.remove = (uuid: string) => {
            $rootScope.$broadcast(Eventnames.REMOVE_FROM_CARD, uuid);
        }
    };

    return directive;
}]);
