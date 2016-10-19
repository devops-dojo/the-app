/// <reference path="../services/product.ts"/>

class ProductDetailController {
    product: IProduct;

    static $inject = ['$scope', '$routeParams', '$rootScope', 'productService'];

    constructor(private $scope,
                private $routeParams,
                private $rootScope: ng.IScope,
                private productService: IProductService
        ) {

        productService.getProducts().then((data: IProduct[]) =>  {
            var products = _.filter(data, (item: IProduct) => item.urlname == $routeParams.urlname)
            if(!_.isEmpty(products)){
                this.product = products[0];
            }else{
                this.$rootScope.$emit(Eventnames.ADD_ALERT_MESSAGE, {type : "danger", message : "Could not find Product"});
            }
        });

        $scope.vm = this;
    }
}

eshop.controller('productDetailController', ProductDetailController);
