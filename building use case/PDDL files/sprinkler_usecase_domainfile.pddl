(define (domain sprinkler_usecase-strips)
	(:requirements :strips)
	(:predicates (has_on_sprinkler ?room) (has_off_sprinkler ?room) (has_on_server ?room) (has_off_server ?room))
	
	(:action switch_on_sprinkler
		:parameters (?room)
		:precondition (and (has_off_sprinkler ?room) (has_off_server ?room))
		:effect (and (not (has_off_sprinkler ?room)) (has_on_sprinkler ?room))
	)

	(:action switch_off_server
		:parameters (?room)
		:precondition (has_on_server ?room)
		:effect (and (not (has_on_server ?room)) (has_off_server ?room))	
	)
	
)
