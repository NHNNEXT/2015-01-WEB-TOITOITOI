document.addEventListener("DOMContentLoaded", function() {
	headerCarousel = $(".carousel-full-container").owlCarousel({
		items:1,
		autoHeightClass: 'carousel-horse',
		dots:true,
		responsive : {
			600 : {
				items:2,
				dots:false
			}
		}
	});
	cardCarousel = $(".carousel-card-container").owlCarousel({
		lazyContent:true,
		items:1,
		itemElement:'article',
		dots:false
		// items: 2,
		// loop: true,
		// center: true
	});
});