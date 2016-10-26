eshop.directive("shopNavigation", () => {
    var directive: ng.IDirective = {};

    directive.restrict = "AE";
    directive.templateUrl = "/partials/navigation.html";
    directive.replace = true;

    return directive;
});
