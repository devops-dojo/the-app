/// <reference path="../services/product.ts"/>

class RecommendationController {
    products: any[] = [];

    static $inject = ['$scope', 'productService'];

    constructor(private $scope, private productService:IProductService) {
         productService.getProducts().then((data: IProduct[]) => {
           var result = _.shuffle(data);
           this.products = _.first(result, 2);
        });

        $scope.vm = this;
    }
}

eshop.controller('recommendationController', RecommendationController);


