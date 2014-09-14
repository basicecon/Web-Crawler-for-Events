Performance:

test case1:
	http://calendar.boston.com/lowell_ma/events/show/274127485-mrt-presents-shakespeares-will
result:
	(Generated 6 events, the 7th take longer to come out)

	0 th event__http://calendar.boston.com/lowell_ma/events/show/371472211-orange-is-the-new-black-author-piper-kerman
	1 th event__http://calendar.boston.com/lowell_ma/events/show/371502231-slipknot
	2 th event__http://calendar.boston.com/lowell_ma/events/show/370864451-r5
	3 th event__http://calendar.boston.com/lowell_ma/events/show/370955267-bassnectar
	4 th event__http://calendar.boston.com/lowell_ma/events/show/371299836-wicked-halloween
	5 th event__http://calendar.boston.com/lowell_ma/events/show/370480970-five-finger-death-punch
----------------------------------------------------------------------------------------------------------------
test case2:
	http://www.eventbrite.com/e/oktoberfest-by-the-bay-2014-tickets-12291900405?aff=ehometext&rank=2
result:
	(Generated 10 events)

	0 th event__http://www.eventbrite.com/e/sasa-official-back-to-school-party-tickets-13005288167?aff=ehomecard&amp;rank=5
	1 th event__http://www.eventbrite.com/e/when-it-rainsplant-a-garden-tickets-12608413103?aff=ehomecard&amp;rank=4
	2 th event__http://www.eventbrite.com/e/embracing-the-divine-feminine-tickets-12669315263?aff=ehomecard&amp;rank=10
	3 th event__http://www.eventbrite.com/e/sigep-indiana-alpha-annual-golf-outing-tickets-12687463545?aff=ehomecard&amp;rank=12
	4 th event__http://www.eventbrite.com/e/tour-de-taco-tickets-12946309761?aff=ehomecard&amp;rank=2
	5 th event__http://www.eventbrite.com/e/21st-century-scholars-fall-counselor-update-100214-tickets-12854206277?aff=ehomecard&amp;rank=8
	6 th event__http://www.eventbrite.com/e/2014-feast-of-the-hunters-moon-tickets-11903444523?aff=ehomecard&amp;rank=1
	7 th event__http://www.eventbrite.com/e/2014-hammer-down-cancer-luncheon-to-support-the-purdue-center-for-cancer-research-tickets-12249300989?aff=ehomecard&amp;rank=6
	8 th event__http://www.eventbrite.com/e/l2-learn-lead-lafayette-leadership-simulcast-brunch-tickets-12063531347?aff=ehomecard&amp;rank=11
	9 th event__http://www.eventbrite.com/e/take-chances-for-kids-lafayette-tickets-12747812049?aff=ehomecard&amp;rank=7
----------------------------------------------------------------------------------------------------------------
test case3:
	http://www.sfmoma.org/exhib_events/exhibitions/513
result:
	(Generated 8 events while searching to the end)

	0 th event__http://www.sfmoma.org/exhib_events/exhibitions/586
	1 th event__http://www.sfmoma.org/exhib_events/exhibitions/511
	2 th event__http://www.sfmoma.org/exhib_events/exhibitions/576
	3 th event__http://www.sfmoma.org/exhib_events/exhibitions/582
	4 th event__http://www.sfmoma.org/exhib_events/exhibitions/577
	5 th event__http://www.sfmoma.org/exhib_events/exhibitions/585
	6 th event__http://www.sfmoma.org/exhib_events/exhibitions/575
	7 th event__http://www.sfmoma.org/exhib_events/exhibitions/447
----------------------------------------------------------------------------------------------------------------
test case4:
	http://events.stanford.edu/events/353/35309/
result:
	(Generated 10 events)

	1 th event__http://events.stanford.edu/events/353/35301/
	2 th event__http://events.stanford.edu/events/353/35303/
	3 th event__http://events.stanford.edu/events/353/35305/
	4 th event__http://events.stanford.edu/events/353/35311/
	5 th event__http://events.stanford.edu/events/353/35313/
	6 th event__http://events.stanford.edu/events/353/35315/
	7 th event__http://events.stanford.edu/events/353/35317/
	8 th event__http://events.stanford.edu/events/353/35319/
	9 th event__http://events.stanford.edu/events/353/35321/
----------------------------------------------------------------------------------------------------------------
test case5:
	http://www.workshopsf.org/?page_id=140&id=1328
result:
	(Generated 10 events)

	0 th event__http://www.workshopsf.org/?page_id=89
	1 th event__http://www.workshopsf.org/?page_id=140&id=2233
	2 th event__http://www.workshopsf.org/?page_id=140&id=2226
	3 th event__http://www.workshopsf.org/?p=861
	4 th event__http://www.workshopsf.org/?feed=rss2
	5 th event__http://www.workshopsf.org/?feed=comments-rss2
	6 th event__http://www.workshopsf.org/?page_id=659
	7 th event__http://www.workshopsf.org/?p=192
	8 th event__http://www.workshopsf.org/?p=194
	9 th event__http://www.workshopsf.org/?page_id=140&id=2254
----------------------------------------------------------------------------------------------------------------

How To Run:

javac -cp jsoup-1.6.2.jar:mysql-connector-java-5.1.15-bin.jar Crawler.java
java -cp .:jsoup-1.6.2.jar:mysql-connector-java-5.1.15-bin.jar Crawler
