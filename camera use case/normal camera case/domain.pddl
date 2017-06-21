
(define (domain cameras)
  (:requirements :strips :negative-preconditions
                 :conditional-effects :disjunctive-preconditions)
;;  (:constants ON OFF)
  (:types cam region)
  (:predicates
         (upgraded ?c)
         (iscovering ?c ?r)
         (cancover ?c ?r)
         (ison ?c)
         (critical ?r)
         (check)(safe)
  )
  ;; (:functions (total-cost)
  ;; )

  (:action UPGRADE
        :parameters (?c - cam)
        :precondition (and (not(check)) (safe)
                 (not (ison ?c))
                 (not (upgraded ?c)))
        :effect (and (check) (upgraded ?c))
  )
  (:action TURN-ON
        :parameters  (?c - cam)
        :precondition (and (not(check)) (safe)
                      (not (ison ?c)))
        :effect (and (check) (ison ?c)
                     (forall (?r - region)
                        (when (cancover ?c ?r)
                              (iscovering ?c ?r))
                     ))
  )

  (:action TURN-OFF
        :parameters  (?c - cam)
        :precondition (and (not(check)) (safe)
                      (ison ?c))
        :effect (and (check) (not (ison ?c))
                     (forall (?r - region)
                        (when (cancover ?c ?r)
                              (not (iscovering ?c ?r)))
                     ))
  )
  (:action SAFETY-CHECK
    :parameters ()
        :precondition (check)
        :effect
                (and    (not(check))
                        (when
                                (exists (?r - region)
                                        (and (critical ?r)
                                         (forall (?c - cam) (not (iscovering ?c ?r)))))
                                (not (safe))
                        )
                )
  )
)
