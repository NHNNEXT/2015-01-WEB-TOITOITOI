document.addEventListener("DOMContentLoaded", function() {
	$(".carousel-full-container").owlCarousel({
		items:1,
		autoHeight: true,
		loop: true,
		autoHeightClass: 'carousel-horse',
		responsive : {
			600 : {
				items:2,
				autoHeight: false,
				loop: false
			}
		}
	});
	cardCarousel = $(".carousel-card-container").owlCarousel({
		lazyContent:true,
		items:1,
		itemElement:'article'
		// items: 2,
		// loop: true,
		// center: true
	});
});