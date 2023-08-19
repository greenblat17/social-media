package com.greenblat.socialmedia.repository;

import com.greenblat.socialmedia.model.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findByAuthor_Id(Long authorId);

    @Query(value = """
            SELECT p.id, p.title, p.content, p.created_at, p.user_id
            FROM post p
                     INNER JOIN
                 (SELECT res.user_id, max(res.created_at) latest_date
                  from (SELECT MAX(p.created_at) created_at, p.user_id
                        from post p
                                 inner join public.user_relation ur on p.user_id = ur.following_user
                        WHERE ur.user_id = 4
                          AND ur.relation_type IN ('FRIEND', 'FOLLOWER')
                        group by p.user_id, p.id, p.title, p.content
                        ORDER BY created_at DESC) as res
                  group by res.user_id) as subquery
                 ON p.user_id = subquery.user_id AND p.created_at = subquery.latest_date
            """,
            nativeQuery = true)
    Page<Post> findByFollowerUser(Pageable pageable, @Param("user_id") Long followerUserId);
}
