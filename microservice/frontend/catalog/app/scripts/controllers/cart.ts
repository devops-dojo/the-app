// <reference path="../services/cart.ts"/>

interface ICartScope extends ng.IScope {
    vm: CartController;
}

class CartController {
    private cartId: string;
    private cartBaseURL: string;
    private globalDiscount: number;
    private cartItems: ICartItem[] = [];

    static $inject = ['$scope', '$rootScope', 'cartServiceResolver', 'configuration'];

    constructor(private $scope: ICartScope,
                private $rootScope: ng.IScope,
                private cartService: ICartService,
                private configuration: IConfiguration) {
        this.cartId = cartService.getCartId();
        this.cartService.getAll().then((data: ICartItem[]) => this.cartItems = data );

        this.cartBaseURL = configuration.CHECKOUT_BASE_URL;
        this.globalDiscount = configuration.GLOBAL_DISCOUNT;

        $scope.vm = this;

        $scope.$on(Eventnames.ADD_TO_CART, (event: ng.IAngularEvent, product: IProduct) => {
            this.add(product).then((data:any)  => this.cartService.getAll().then((data: ICartItem[]) => this.cartItems = data ));
        });

        $scope.$on(Eventnames.REMOVE_FROM_CARD, (event: ng.IAngularEvent, uuid: string) => {
            this.remove(uuid).then((data)  => {
              this.cartService.getAll().then((data: ICartItem[]) => this.cartItems = data );
            });
        });
    }

    add(product: IProduct): ng.IPromise<boolean> {
        return this.cartService.add(product);
    }

    remove(uuid: string): ng.IPromise<boolean> {
      return this.cartService.remove(uuid);
    }

    getAll(): ng.IPromise<ICartItem[]> {
        return this.cartService.getAll();
    }

    getTotalSum(): number {
        var sum:number = 0;
        _.each(this.cartItems, function(elem:ICartItem){
            sum += elem.product.price;
        });
              
        sum = sum - (sum * (this.globalDiscount/100))
        return sum;
    }

    getDiscountSum(): number {
        var sum:number = 0;
        _.each(this.cartItems, function(elem:ICartItem){
            sum += elem.product.price;
        });
      
        sum = sum * (this.globalDiscount/100);
        return sum;
    }

}

eshop.controller('cartController', CartController);

