interface IShopAddToCartScope extends ng.IScope {
    product :IProduct
    add(product :IProduct);
}

eshop.directive("shopAddToCart", ['$rootScope', ($rootScope):ng.IDirective => {
    var directive:ng.IDirective = {};

    directive.restrict = "AE";
    directive.templateUrl = "/partials/addtocart.html";
    directive.replace = true;

    directive.scope = {
        product: "=product"
    };

    directive.link = (scope:IShopAddToCartScope) => {
        scope.add = (product:IProduct) => {
            $rootScope.$broadcast(Eventnames.ADD_TO_CART, product);
        }
    };

    return directive;
}]);