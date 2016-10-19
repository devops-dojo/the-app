/// <reference path="../services/product.ts"/>

interface IProductCatalogScope extends ng.IScope {
    vm: ProductListController;
}

class ProductListController {
    productType: String;
    headline: String;
    products: IProduct[];

    static $inject = ['$scope', '$routeParams', 'productService'];

    constructor(private $scope,
                private $routeParams,
                private productService: IProductService) {
        this.productType = $routeParams.productType;
        this.headline = "Choose your product";

        productService.getProducts().then((data: IProduct[]) =>  {
            this.products = _.filter(data, (item) => item.productType.toLowerCase() == this.productType)
        });

        $scope.vm = this;
    }
}

eshop.controller('productListController', ProductListController);
