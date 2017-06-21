(define (problem problem1)
        (:domain cameras)
        (:objects
                c1 c2 c3 - cam
                r1 r2 r3 r4 r5 - region)

        (:INIT
                (cancover c1 r1) (cancover c1 r2)
                (cancover c2 r2) (cancover c2 r3) (cancover c2 r4)
                (cancover c3 r4) (cancover c3 r5)
                (ison c1)
                (iscovering c1 r1) (iscovering c1 r2)
                ;;(ison c2)
                ;;(iscovering c2 r2) (iscovering c2 r3) (iscovering c2 r4)
                (ison c3)
                (iscovering c3 r4) (iscovering c3 r5)
                (critical r2) (critical r4)
                (check)(safe)
        )
        (:goal
                (AND (upgraded  c1) (upgraded  c2) (upgraded c3) 
                	(ison c1) (ison c2) (ison c3)(safe) (not (check)))
        )
        (:metric minimize (total-time))

)
