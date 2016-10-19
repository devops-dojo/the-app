eshop.directive("shopCart", () => {
    var directive: ng.IDirective = {};

    directive.restrict = "AE";
    directive.templateUrl = "/partials/cart.html";
    directive.replace = false;

    directive.scope = {};

    return directive;
});
